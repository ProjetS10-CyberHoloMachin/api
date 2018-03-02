import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { InfoDefinition } from './info-definition.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<InfoDefinition>;

@Injectable()
export class InfoDefinitionService {

    private resourceUrl =  SERVER_API_URL + 'api/info-definitions';

    constructor(private http: HttpClient) { }

    create(infoDefinition: InfoDefinition): Observable<EntityResponseType> {
        const copy = this.convert(infoDefinition);
        return this.http.post<InfoDefinition>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(infoDefinition: InfoDefinition): Observable<EntityResponseType> {
        const copy = this.convert(infoDefinition);
        return this.http.put<InfoDefinition>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<InfoDefinition>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<InfoDefinition[]>> {
        const options = createRequestOption(req);
        return this.http.get<InfoDefinition[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<InfoDefinition[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: InfoDefinition = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<InfoDefinition[]>): HttpResponse<InfoDefinition[]> {
        const jsonResponse: InfoDefinition[] = res.body;
        const body: InfoDefinition[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to InfoDefinition.
     */
    private convertItemFromServer(infoDefinition: InfoDefinition): InfoDefinition {
        const copy: InfoDefinition = Object.assign({}, infoDefinition);
        return copy;
    }

    /**
     * Convert a InfoDefinition to a JSON which can be sent to the server.
     */
    private convert(infoDefinition: InfoDefinition): InfoDefinition {
        const copy: InfoDefinition = Object.assign({}, infoDefinition);
        return copy;
    }
}
