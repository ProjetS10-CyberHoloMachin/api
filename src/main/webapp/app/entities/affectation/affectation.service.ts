import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Affectation } from './affectation.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Affectation>;

@Injectable()
export class AffectationService {

    private resourceUrl =  SERVER_API_URL + 'api/affectations';

    constructor(private http: HttpClient) { }

    create(affectation: Affectation): Observable<EntityResponseType> {
        const copy = this.convert(affectation);
        return this.http.post<Affectation>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(affectation: Affectation): Observable<EntityResponseType> {
        const copy = this.convert(affectation);
        return this.http.put<Affectation>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Affectation>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Affectation[]>> {
        const options = createRequestOption(req);
        return this.http.get<Affectation[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Affectation[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Affectation = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Affectation[]>): HttpResponse<Affectation[]> {
        const jsonResponse: Affectation[] = res.body;
        const body: Affectation[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Affectation.
     */
    private convertItemFromServer(affectation: Affectation): Affectation {
        const copy: Affectation = Object.assign({}, affectation);
        return copy;
    }

    /**
     * Convert a Affectation to a JSON which can be sent to the server.
     */
    private convert(affectation: Affectation): Affectation {
        const copy: Affectation = Object.assign({}, affectation);
        return copy;
    }
}
