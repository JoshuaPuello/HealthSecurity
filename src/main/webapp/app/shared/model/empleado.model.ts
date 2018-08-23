import { Moment } from 'moment';
import { IUser } from 'app/core/user/user.model';

export const enum TipoDocumentoEnum {
    CEDULA_CIUDADANIA = 'CEDULA_CIUDADANIA',
    CEDULA_EXTRANJERIA = 'CEDULA_EXTRANJERIA',
    TARJETA_IDENTIDAD = 'TARJETA_IDENTIDAD'
}

export interface IEmpleado {
    id?: number;
    tipo_documento?: TipoDocumentoEnum;
    documento?: string;
    primer_nombre?: string;
    segundo_nombre?: string;
    primer_apellido?: string;
    segundo_apellido?: string;
    direccion?: string;
    telefono?: string;
    nacimiento?: Moment;
    user?: IUser;
}

export class Empleado implements IEmpleado {
    constructor(
        public id?: number,
        public tipo_documento?: TipoDocumentoEnum,
        public documento?: string,
        public primer_nombre?: string,
        public segundo_nombre?: string,
        public primer_apellido?: string,
        public segundo_apellido?: string,
        public direccion?: string,
        public telefono?: string,
        public nacimiento?: Moment,
        public user?: IUser
    ) {}
}
