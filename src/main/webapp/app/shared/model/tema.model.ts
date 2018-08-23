import { IWiki } from 'app/shared/model/wiki.model';

export interface ITema {
    id?: number;
    nombre?: string;
    descripcion?: any;
    wikis?: IWiki[];
}

export class Tema implements ITema {
    constructor(public id?: number, public nombre?: string, public descripcion?: any, public wikis?: IWiki[]) {}
}
