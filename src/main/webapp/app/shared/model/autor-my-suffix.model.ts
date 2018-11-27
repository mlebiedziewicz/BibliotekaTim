export interface IAutorMySuffix {
    id?: number;
    imienazwisko?: string;
}

export class AutorMySuffix implements IAutorMySuffix {
    constructor(public id?: number, public imienazwisko?: string) {}
}
