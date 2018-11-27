import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IAutorMySuffix } from 'app/shared/model/autor-my-suffix.model';

type EntityResponseType = HttpResponse<IAutorMySuffix>;
type EntityArrayResponseType = HttpResponse<IAutorMySuffix[]>;

@Injectable({ providedIn: 'root' })
export class AutorMySuffixService {
    public resourceUrl = SERVER_API_URL + 'api/autors';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/autors';

    constructor(private http: HttpClient) {}

    create(autor: IAutorMySuffix): Observable<EntityResponseType> {
        return this.http.post<IAutorMySuffix>(this.resourceUrl, autor, { observe: 'response' });
    }

    update(autor: IAutorMySuffix): Observable<EntityResponseType> {
        return this.http.put<IAutorMySuffix>(this.resourceUrl, autor, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IAutorMySuffix>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IAutorMySuffix[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IAutorMySuffix[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
