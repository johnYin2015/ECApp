package com.diabin.latte.compiler;

import com.diabin.latte.annotations.AppRegisterGenerator;
import com.diabin.latte.annotations.EntryGenerator;
import com.diabin.latte.annotations.PayEntryGenerator;
import com.google.auto.service.AutoService;

import java.lang.annotation.Annotation;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.AnnotationValueVisitor;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;

@SuppressWarnings("unused")
@AutoService(Processor.class)
public final class LatteProcessor extends AbstractProcessor {

    private Set<Class<? extends Annotation>> getSupportedAnnotations() {
        final Set<Class<? extends Annotation>> supportedAnnotations = new LinkedHashSet<>();
        supportedAnnotations.add(EntryGenerator.class);
        supportedAnnotations.add(PayEntryGenerator.class);
        supportedAnnotations.add(AppRegisterGenerator.class);
        return supportedAnnotations;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        final Set<String> types = new LinkedHashSet<>();
        final Set<Class<? extends Annotation>> supportedAnnotations = getSupportedAnnotations();
        for (Class<? extends Annotation> supportedAnnotation : supportedAnnotations) {
            String type = supportedAnnotation.getCanonicalName();
            types.add(type);
        }
        return types;
    }

    private void scan(RoundEnvironment env, Class<? extends Annotation> annotations,
                      AnnotationValueVisitor visitor) {
        for (Element typeElement : env.getElementsAnnotatedWith(annotations)) {

            final List<? extends AnnotationMirror> annotationMirrors = typeElement.getAnnotationMirrors();

            for (AnnotationMirror annotationMirror : annotationMirrors) {

                final Map<? extends ExecutableElement, ? extends AnnotationValue> elementValues
                        = annotationMirror.getElementValues();

                for (Map.Entry<? extends ExecutableElement, ? extends AnnotationValue> entry :
                        elementValues.entrySet()) {

                    //noinspection unchecked
                    entry.getValue().accept(visitor, null);

                }
            }
        }
    }

    private void generateCode(RoundEnvironment env){
        final EntryVisitor visitor = new EntryVisitor();
        visitor.setFiler(processingEnv.getFiler());
        scan(env,EntryGenerator.class,visitor);
    }

    private void generatePayCode(RoundEnvironment env){
        final PayEntryVisitor visitor = new PayEntryVisitor();
        visitor.setFiler(processingEnv.getFiler());
        scan(env,PayEntryGenerator.class,visitor);
    }

    private void generateAppRegisterCode(RoundEnvironment env){
        final AppRegisterVisitor visitor = new AppRegisterVisitor();
        visitor.setFiler(processingEnv.getFiler());
        scan(env,AppRegisterGenerator.class,visitor);
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment env) {
        generateCode(env);
        generatePayCode(env);
        generateAppRegisterCode(env);
        return true;
    }
}
