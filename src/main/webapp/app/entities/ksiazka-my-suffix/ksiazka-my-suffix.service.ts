import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IKsiazkaMySuffix } from 'app/shared/model/ksiazka-my-suffix.model';

type EntityResponseType = HttpResponse<IKsiazkaMySuffix>;
type EntityArrayResponseType = HttpResponse<IKsiazkaMySuffix[]>;

@Injectable({ providedIn: 'root' })
export class KsiazkaMySuffixService {
    public resourceUrl = SERVER_API_URL + 'api/ksiazkas';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/ksiazkas';

    constructor(private http: HttpClient) {}

    create(ksiazka: IKsiazkaMySuffix): Observable<EntityResponseType> {
        return this.http.post<IKsiazkaMySuffix>(this.resourceUrl, ksiazka, { observe: 'response' });
    }

    update(ksiazka: IKsiazkaMySuffix): Observable<EntityResponseType> {
        return this.http.put<IKsiazkaMySuffix>(this.resourceUrl, ksiazka, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IKsiazkaMySuffix>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IKsiazkaMySuffix[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IKsiazkaMySuffix[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
