import { IUser } from 'app/core/user/user.model';
import { IPersona } from 'app/shared/model//persona.model';

export interface IUsuario {
    id?: number;
    cargo?: string;
    user?: IUser;
    persona?: IPersona;
}

export class Usuario implements IUsuario {
    constructor(public id?: number, public cargo?: string, public user?: IUser, public persona?: IPersona) {}
}
