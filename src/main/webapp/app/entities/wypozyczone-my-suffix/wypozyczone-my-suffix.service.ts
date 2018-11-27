import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IWypozyczoneMySuffix } from 'app/shared/model/wypozyczone-my-suffix.model';

type EntityResponseType = HttpResponse<IWypozyczoneMySuffix>;
type EntityArrayResponseType = HttpResponse<IWypozyczoneMySuffix[]>;

@Injectable({ providedIn: 'root' })
export class WypozyczoneMySuffixService {
    public resourceUrl = SERVER_API_URL + 'api/wypozyczones';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/wypozyczones';

    constructor(private http: HttpClient) {}

    create(wypozyczone: IWypozyczoneMySuffix): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(wypozyczone);
        return this.http
            .post<IWypozyczoneMySuffix>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(wypozyczone: IWypozyczoneMySuffix): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(wypozyczone);
        return this.http
            .put<IWypozyczoneMySuffix>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IWypozyczoneMySuffix>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IWypozyczoneMySuffix[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IWypozyczoneMySuffix[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(wypozyczone: IWypozyczoneMySuffix): IWypozyczoneMySuffix {
        const copy: IWypozyczoneMySuffix = Object.assign({}, wypozyczone, {
            datawypozyczenia:
                wypozyczone.datawypozyczenia != null && wypozyczone.datawypozyczenia.isValid()
                    ? wypozyczone.datawypozyczenia.format(DATE_FORMAT)
                    : null,
            dataoddania:
                wypozyczone.dataoddania != null && wypozyczone.dataoddania.isValid() ? wypozyczone.dataoddania.format(DATE_FORMAT) : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.datawypozyczenia = res.body.datawypozyczenia != null ? moment(res.body.datawypozyczenia) : null;
            res.body.dataoddania = res.body.dataoddania != null ? moment(res.body.dataoddania) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((wypozyczone: IWypozyczoneMySuffix) => {
                wypozyczone.datawypozyczenia = wypozyczone.datawypozyczenia != null ? moment(wypozyczone.datawypozyczenia) : null;
                wypozyczone.dataoddania = wypozyczone.dataoddania != null ? moment(wypozyczone.dataoddania) : null;
            });
        }
        return res;
    }
}
