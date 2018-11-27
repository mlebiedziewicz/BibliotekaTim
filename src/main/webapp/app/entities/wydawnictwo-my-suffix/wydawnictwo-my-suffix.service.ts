import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IWydawnictwoMySuffix } from 'app/shared/model/wydawnictwo-my-suffix.model';

type EntityResponseType = HttpResponse<IWydawnictwoMySuffix>;
type EntityArrayResponseType = HttpResponse<IWydawnictwoMySuffix[]>;

@Injectable({ providedIn: 'root' })
export class WydawnictwoMySuffixService {
    public resourceUrl = SERVER_API_URL + 'api/wydawnictwos';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/wydawnictwos';

    constructor(private http: HttpClient) {}

    create(wydawnictwo: IWydawnictwoMySuffix): Observable<EntityResponseType> {
        return this.http.post<IWydawnictwoMySuffix>(this.resourceUrl, wydawnictwo, { observe: 'response' });
    }

    update(wydawnictwo: IWydawnictwoMySuffix): Observable<EntityResponseType> {
        return this.http.put<IWydawnictwoMySuffix>(this.resourceUrl, wydawnictwo, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IWydawnictwoMySuffix>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IWydawnictwoMySuffix[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IWydawnictwoMySuffix[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
