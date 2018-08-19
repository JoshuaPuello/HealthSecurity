import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IWiki } from 'app/shared/model/wiki.model';

type EntityResponseType = HttpResponse<IWiki>;
type EntityArrayResponseType = HttpResponse<IWiki[]>;

@Injectable({ providedIn: 'root' })
export class WikiService {
    private resourceUrl = SERVER_API_URL + 'api/wikis';

    constructor(private http: HttpClient) {}

    create(wiki: IWiki): Observable<EntityResponseType> {
        return this.http.post<IWiki>(this.resourceUrl, wiki, { observe: 'response' });
    }

    update(wiki: IWiki): Observable<EntityResponseType> {
        return this.http.put<IWiki>(this.resourceUrl, wiki, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IWiki>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IWiki[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
