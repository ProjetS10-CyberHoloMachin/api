import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { BuildingData } from './building-data.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<BuildingData>;

@Injectable()
export class BuildingDataService {

    private resourceUrl =  SERVER_API_URL + 'api/building-data';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/building-data';

    constructor(private http: HttpClient) { }

    create(buildingData: BuildingData): Observable<EntityResponseType> {
        const copy = this.convert(buildingData);
        return this.http.post<BuildingData>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(buildingData: BuildingData): Observable<EntityResponseType> {
        const copy = this.convert(buildingData);
        return this.http.put<BuildingData>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<BuildingData>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<BuildingData[]>> {
        const options = createRequestOption(req);
        return this.http.get<BuildingData[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<BuildingData[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    search(req?: any): Observable<HttpResponse<BuildingData[]>> {
        const options = createRequestOption(req);
        return this.http.get<BuildingData[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<BuildingData[]>) => this.convertArrayResponse(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: BuildingData = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<BuildingData[]>): HttpResponse<BuildingData[]> {
        const jsonResponse: BuildingData[] = res.body;
        const body: BuildingData[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to BuildingData.
     */
    private convertItemFromServer(buildingData: BuildingData): BuildingData {
        const copy: BuildingData = Object.assign({}, buildingData);
        return copy;
    }

    /**
     * Convert a BuildingData to a JSON which can be sent to the server.
     */
    private convert(buildingData: BuildingData): BuildingData {
        const copy: BuildingData = Object.assign({}, buildingData);
        return copy;
    }
}
