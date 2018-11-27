import { Moment } from 'moment';
import { IUser } from 'app/core/user/user.model';
import { IKsiazkaMySuffix } from 'app/shared/model//ksiazka-my-suffix.model';

export interface IWypozyczoneMySuffix {
    id?: number;
    datawypozyczenia?: Moment;
    dataoddania?: Moment;
    uzytkowniks?: IUser[];
    ksiazkas?: IKsiazkaMySuffix[];
}

export class WypozyczoneMySuffix implements IWypozyczoneMySuffix {
    constructor(
        public id?: number,
        public datawypozyczenia?: Moment,
        public dataoddania?: Moment,
        public uzytkowniks?: IUser[],
        public ksiazkas?: IKsiazkaMySuffix[]
    ) {}
}
