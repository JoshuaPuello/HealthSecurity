import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IAreaRiesgo } from 'app/shared/model/area-riesgo.model';

type EntityResponseType = HttpResponse<IAreaRiesgo>;
type EntityArrayResponseType = HttpResponse<IAreaRiesgo[]>;

@Injectable({ providedIn: 'root' })
export class AreaRiesgoService {
    private resourceUrl = SERVER_API_URL + 'api/area-riesgos';

    constructor(private http: HttpClient) {}

    create(areaRiesgo: IAreaRiesgo): Observable<EntityResponseType> {
        return this.http.post<IAreaRiesgo>(this.resourceUrl, areaRiesgo, { observe: 'response' });
    }

    update(areaRiesgo: IAreaRiesgo): Observable<EntityResponseType> {
        return this.http.put<IAreaRiesgo>(this.resourceUrl, areaRiesgo, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IAreaRiesgo>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IAreaRiesgo[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
