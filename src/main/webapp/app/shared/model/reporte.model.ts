import { IRespuesta } from 'app/shared/model//respuesta.model';
import { IUser } from 'app/core/user/user.model';

export const enum ValoracionEnum {
    LEVE = 'LEVE',
    MODERADO = 'MODERADO',
    CRITICO = 'CRITICO'
}

export const enum LugarEventoEnum {
    DENTRO_EMPRESA = 'DENTRO_EMPRESA',
    FUERA_EMPRESA = 'FUERA_EMPRESA'
}

export const enum TipoEventoEnum {
    ACCIDENTE = 'ACCIDENTE',
    INCIDENTE = 'INCIDENTE'
}

export interface IReporte {
    id?: number;
    valoracion?: ValoracionEnum;
    nombre?: string;
    descripcion?: any;
    accinones_realizadas?: any;
    evidenciaContentType?: string;
    evidencia?: any;
    lugar_evento?: LugarEventoEnum;
    tipo_evento?: TipoEventoEnum;
    bLabores?: boolean;
    bReportado?: boolean;
    respuesta?: IRespuesta;
    user?: IUser;
}

export class Reporte implements IReporte {
    constructor(
        public id?: number,
        public valoracion?: ValoracionEnum,
        public nombre?: string,
        public descripcion?: any,
        public accinones_realizadas?: any,
        public evidenciaContentType?: string,
        public evidencia?: any,
        public lugar_evento?: LugarEventoEnum,
        public tipo_evento?: TipoEventoEnum,
        public bLabores?: boolean,
        public bReportado?: boolean,
        public respuesta?: IRespuesta,
        public user?: IUser
    ) {
        this.bLabores = false;
        this.bReportado = false;
    }
}
