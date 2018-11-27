import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IGatunekMySuffix } from 'app/shared/model/gatunek-my-suffix.model';

type EntityResponseType = HttpResponse<IGatunekMySuffix>;
type EntityArrayResponseType = HttpResponse<IGatunekMySuffix[]>;

@Injectable({ providedIn: 'root' })
export class GatunekMySuffixService {
    public resourceUrl = SERVER_API_URL + 'api/gatuneks';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/gatuneks';

    constructor(private http: HttpClient) {}

    create(gatunek: IGatunekMySuffix): Observable<EntityResponseType> {
        return this.http.post<IGatunekMySuffix>(this.resourceUrl, gatunek, { observe: 'response' });
    }

    update(gatunek: IGatunekMySuffix): Observable<EntityResponseType> {
        return this.http.put<IGatunekMySuffix>(this.resourceUrl, gatunek, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IGatunekMySuffix>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IGatunekMySuffix[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IGatunekMySuffix[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
