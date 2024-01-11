package com.example.base.web.advisor;

import com.example.base.common.util.JsonUtils;
import com.example.base.web.dto.ApiResponseGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.GenericTypeResolver;
import org.springframework.core.MethodParameter;
import org.springframework.core.ResolvableType;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.lang.reflect.Type;
import java.util.Objects;

@Slf4j
@RestControllerAdvice(basePackages = "com.example.base")
public class ApiResponseWrappingAdvisor implements ResponseBodyAdvice<Object> {
    private Type getGenericType(MethodParameter returnType) {
        if (HttpEntity.class.isAssignableFrom(returnType.getParameterType())) {
            return ResolvableType.forType(returnType.getGenericParameterType()).getGeneric().getType();
        } else {
            return returnType.getGenericParameterType();
        }
    }

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        Type type = GenericTypeResolver.resolveType(getGenericType(returnType),
                returnType.getContainingClass());

        // void 타입은 예외처리
        if (Void.class.getName().equals(type.getTypeName())) {
            return false;
        }

        //ByteArrayHttpMessageConverter, ResourceHttpMessageConverter는 예외처리
        //ByteArrayHttpMessageConverter: 이미지나 기타 이진 데이터 다룰 때 유용
        //ResourceHttpMessageConverter: 파일 업로드/다운로드
        return !converterType.isAssignableFrom(ByteArrayHttpMessageConverter.class) &&
                !converterType.isAssignableFrom(ResourceHttpMessageConverter.class);
    }

    @Override
    public Object beforeBodyWrite(@Nullable Object body,
                                  @NonNull MethodParameter returnType,
                                  @NonNull MediaType selectedContentType,
                                  @NonNull Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  @NonNull ServerHttpRequest request,
                                  @NonNull ServerHttpResponse response) {

        HttpStatus responseStatus = HttpStatus.valueOf(
                ((ServletServerHttpResponse) response).getServletResponse().getStatus()
        );

        if (Objects.isNull(body)) {
            if (responseStatus.isError()) {
                return ApiResponseGenerator.fail();
            }
            return HttpStatus.CREATED.equals(responseStatus)? ApiResponseGenerator.created(): ApiResponseGenerator.success();
        }

        var apiResponse = responseStatus.isError() ? ApiResponseGenerator.fail(body) : ApiResponseGenerator.success(body);
        log.trace("[ApiResponse] {}", apiResponse);

        // StringHttpMessageConverter 및 하위 클래스에 json 처리
        if (selectedConverterType.isAssignableFrom(StringHttpMessageConverter.class)) {
            try {
                response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
                return JsonUtils.toJson(apiResponse);
            } catch (JsonUtils.JsonEncodeException jpe) {
                log.warn("JSON 처리 중 오류 발생", jpe);
                throw new ApiResponseJsonProcessingException(jpe);
            }
        }

        return apiResponse;
    }
}
