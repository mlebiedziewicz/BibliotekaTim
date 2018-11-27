export interface IKsiazkaMySuffix {
    id?: number;
    tytul?: string;
    tematyka?: string;
    opis?: any;
    autorImienazwisko?: string;
    autorId?: number;
    wydawnictwoNazwa?: string;
    wydawnictwoId?: number;
    gatunekNazwa?: string;
    gatunekId?: number;
}

export class KsiazkaMySuffix implements IKsiazkaMySuffix {
    constructor(
        public id?: number,
        public tytul?: string,
        public tematyka?: string,
        public opis?: any,
        public autorImienazwisko?: string,
        public autorId?: number,
        public wydawnictwoNazwa?: string,
        public wydawnictwoId?: number,
        public gatunekNazwa?: string,
        public gatunekId?: number
    ) {}
}
