import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IReporte } from 'app/shared/model/reporte.model';

type EntityResponseType = HttpResponse<IReporte>;
type EntityArrayResponseType = HttpResponse<IReporte[]>;

@Injectable({ providedIn: 'root' })
export class ReporteService {
    private resourceUrl = SERVER_API_URL + 'api/reportes';

    constructor(private http: HttpClient) {}

    create(reporte: IReporte): Observable<EntityResponseType> {
        return this.http.post<IReporte>(this.resourceUrl, reporte, { observe: 'response' });
    }

    update(reporte: IReporte): Observable<EntityResponseType> {
        return this.http.put<IReporte>(this.resourceUrl, reporte, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IReporte>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IReporte[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
