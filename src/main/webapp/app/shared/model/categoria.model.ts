import { IWiki } from 'app/shared/model/wiki.model';

export interface ICategoria {
    id?: number;
    nombre?: string;
    descripcion?: any;
    wikis?: IWiki[];
}

export class Categoria implements ICategoria {
    constructor(public id?: number, public nombre?: string, public descripcion?: any, public wikis?: IWiki[]) {}
}
