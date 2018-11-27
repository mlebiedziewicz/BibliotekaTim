export interface IWydawnictwoMySuffix {
    id?: number;
    nazwa?: string;
    adres?: string;
    email?: string;
}

export class WydawnictwoMySuffix implements IWydawnictwoMySuffix {
    constructor(public id?: number, public nazwa?: string, public adres?: string, public email?: string) {}
}
