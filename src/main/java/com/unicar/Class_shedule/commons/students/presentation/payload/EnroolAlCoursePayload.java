package com.unicar.Class_shedule.commons.students.presentation.payload;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EnroolAlCoursePayload{

       private   Long idCurso
        ;

        private  String dni;

}
