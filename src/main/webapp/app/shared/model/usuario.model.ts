export interface IUsuario {
    id?: number;
    cargo?: string;
}

export class Usuario implements IUsuario {
    constructor(public id?: number, public cargo?: string) {}
}
