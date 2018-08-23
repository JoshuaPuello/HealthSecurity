import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ITipoRiesgo } from 'app/shared/model/tipo-riesgo.model';

type EntityResponseType = HttpResponse<ITipoRiesgo>;
type EntityArrayResponseType = HttpResponse<ITipoRiesgo[]>;

@Injectable({ providedIn: 'root' })
export class TipoRiesgoService {
    private resourceUrl = SERVER_API_URL + 'api/tipo-riesgos';

    constructor(private http: HttpClient) {}

    create(tipoRiesgo: ITipoRiesgo): Observable<EntityResponseType> {
        return this.http.post<ITipoRiesgo>(this.resourceUrl, tipoRiesgo, { observe: 'response' });
    }

    update(tipoRiesgo: ITipoRiesgo): Observable<EntityResponseType> {
        return this.http.put<ITipoRiesgo>(this.resourceUrl, tipoRiesgo, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ITipoRiesgo>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ITipoRiesgo[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
