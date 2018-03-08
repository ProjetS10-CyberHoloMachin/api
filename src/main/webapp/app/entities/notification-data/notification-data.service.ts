import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { NotificationData } from './notification-data.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<NotificationData>;

@Injectable()
export class NotificationDataService {

    private resourceUrl =  SERVER_API_URL + 'api/notification-data';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/notification-data';

    constructor(private http: HttpClient) { }

    create(notificationData: NotificationData): Observable<EntityResponseType> {
        const copy = this.convert(notificationData);
        return this.http.post<NotificationData>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(notificationData: NotificationData): Observable<EntityResponseType> {
        const copy = this.convert(notificationData);
        return this.http.put<NotificationData>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<NotificationData>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<NotificationData[]>> {
        const options = createRequestOption(req);
        return this.http.get<NotificationData[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<NotificationData[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    search(req?: any): Observable<HttpResponse<NotificationData[]>> {
        const options = createRequestOption(req);
        return this.http.get<NotificationData[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<NotificationData[]>) => this.convertArrayResponse(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: NotificationData = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<NotificationData[]>): HttpResponse<NotificationData[]> {
        const jsonResponse: NotificationData[] = res.body;
        const body: NotificationData[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to NotificationData.
     */
    private convertItemFromServer(notificationData: NotificationData): NotificationData {
        const copy: NotificationData = Object.assign({}, notificationData);
        return copy;
    }

    /**
     * Convert a NotificationData to a JSON which can be sent to the server.
     */
    private convert(notificationData: NotificationData): NotificationData {
        const copy: NotificationData = Object.assign({}, notificationData);
        return copy;
    }
}
