import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Info } from './info.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Info>;

@Injectable()
export class InfoService {

    private resourceUrl =  SERVER_API_URL + 'api/infos';

    constructor(private http: HttpClient) { }

    create(info: Info): Observable<EntityResponseType> {
        const copy = this.convert(info);
        return this.http.post<Info>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(info: Info): Observable<EntityResponseType> {
        const copy = this.convert(info);
        return this.http.put<Info>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Info>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Info[]>> {
        const options = createRequestOption(req);
        return this.http.get<Info[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Info[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Info = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Info[]>): HttpResponse<Info[]> {
        const jsonResponse: Info[] = res.body;
        const body: Info[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Info.
     */
    private convertItemFromServer(info: Info): Info {
        const copy: Info = Object.assign({}, info);
        return copy;
    }

    /**
     * Convert a Info to a JSON which can be sent to the server.
     */
    private convert(info: Info): Info {
        const copy: Info = Object.assign({}, info);
        return copy;
    }
}
