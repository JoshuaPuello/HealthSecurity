import { Moment } from 'moment';

export const enum TipoDocumento {
    CEDULA_CIUDADANIA = 'CEDULA_CIUDADANIA',
    CEDULA_EXTRANJERIA = 'CEDULA_EXTRANJERIA',
    TARJETA_IDENTIDAD = 'TARJETA_IDENTIDAD'
}

export interface IPersona {
    id?: number;
    documento?: string;
    primer_nombre?: string;
    segundo_nombre?: string;
    primer_apellido?: string;
    segundo_apellido?: string;
    correo?: string;
    direccion?: string;
    telefono?: string;
    nacimiento?: Moment;
    tipo_documento?: TipoDocumento;
}

export class Persona implements IPersona {
    constructor(
        public id?: number,
        public documento?: string,
        public primer_nombre?: string,
        public segundo_nombre?: string,
        public primer_apellido?: string,
        public segundo_apellido?: string,
        public correo?: string,
        public direccion?: string,
        public telefono?: string,
        public nacimiento?: Moment,
        public tipo_documento?: TipoDocumento
    ) {}
}
