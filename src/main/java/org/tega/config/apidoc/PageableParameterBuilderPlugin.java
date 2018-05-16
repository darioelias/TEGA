/**
 *  This file is part of TEGA (Tools for Evolutionary and Genetic Analysis)
 *  TEGA website: https://github.com/darioelias/TEGA
 *
 *  Copyright (C) 2018 Dario E. Elias & Eva C. Rueda
 *
 *  TEGA is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Affero General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  TEGA is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU Affero General Public License
 *  along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *  
 *  Additional permission under GNU AGPL version 3 section 7
 *  
 *  If you modify TEGA, or any covered work, by linking or combining it with
 *  STRUCTURE, DISTRUCT or CLUMPP (or a modified version of those programs),
 *  the licensors of TEGA grant you additional permission to convey the resulting work.
 */


/**
 * @author Dario E. Elias
 * @version 1.0 
 */


package org.tega.config.apidoc;

import org.tega.config.Constants;
import com.fasterxml.classmate.ResolvedType;
import com.fasterxml.classmate.TypeResolver;
import com.google.common.base.Function;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import springfox.documentation.schema.ModelReference;
import springfox.documentation.schema.TypeNameExtractor;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.schema.contexts.ModelContext;
import springfox.documentation.spi.service.ParameterBuilderPlugin;
import springfox.documentation.spi.service.contexts.ParameterContext;
import springfox.documentation.swagger.common.SwaggerPluginSupport;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static springfox.documentation.schema.ResolvedTypes.modelRefFactory;
import static springfox.documentation.spi.schema.contexts.ModelContext.inputParam;

@Component
@Order(SwaggerPluginSupport.SWAGGER_PLUGIN_ORDER)
@Profile(Constants.SPRING_PROFILE_SWAGGER)
public class PageableParameterBuilderPlugin implements ParameterBuilderPlugin {
    private final TypeNameExtractor nameExtractor;
    private final TypeResolver resolver;

    @Autowired
    public PageableParameterBuilderPlugin(TypeNameExtractor nameExtractor, TypeResolver resolver) {
        this.nameExtractor = nameExtractor;
        this.resolver = resolver;
    }

    @Override
    public boolean supports(DocumentationType delimiter) {
        return true;
    }

    private Function<ResolvedType, ? extends ModelReference>
    createModelRefFactory(ParameterContext context) {
        ModelContext modelContext = inputParam(context.methodParameter().getParameterType(),
            context.getDocumentationType(),
            context.getAlternateTypeProvider(),
            context.getGenericNamingStrategy(),
            context.getIgnorableParameterTypes());
        return modelRefFactory(modelContext, nameExtractor);
    }

    @Override
    public void apply(ParameterContext context) {
        MethodParameter parameter = context.methodParameter();
        Class<?> type = parameter.getParameterType();
        if (type != null && Pageable.class.isAssignableFrom(type)) {
            Function<ResolvedType, ? extends ModelReference> factory =
                createModelRefFactory(context);

            ModelReference intModel = factory.apply(resolver.resolve(Integer.TYPE));
            ModelReference stringModel = factory.apply(resolver.resolve(List.class, String.class));

            List<Parameter> parameters = newArrayList(
                context.parameterBuilder()
                    .parameterType("query").name("page").modelRef(intModel)
                    .description("Page number of the requested page")
                    .build(),
                context.parameterBuilder()
                    .parameterType("query").name("size").modelRef(intModel)
                    .description("Size of a page")
                    .build(),
                context.parameterBuilder()
                    .parameterType("query").name("sort").modelRef(stringModel).allowMultiple(true)
                    .description("Sorting criteria in the format: property(,asc|desc). "
                        + "Default sort order is ascending. "
                        + "Multiple sort criteria are supported.")
                    .build());

            context.getOperationContext().operationBuilder().parameters(parameters);
        }
    }

}
