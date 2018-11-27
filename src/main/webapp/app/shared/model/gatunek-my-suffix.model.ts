export interface IGatunekMySuffix {
    id?: number;
    nazwa?: string;
}

export class GatunekMySuffix implements IGatunekMySuffix {
    constructor(public id?: number, public nazwa?: string) {}
}
