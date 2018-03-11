import { Component, OnInit } from '@angular/core';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { HttpClient} from '@angular/common/http';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Account, LoginModalService, Principal } from '../shared';
import { Affectation } from '../entities/affectation';
import { Building } from '../entities/building';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../app.constants';

@Component({
    selector: 'jhi-home',
    templateUrl: './home.component.html',
    styleUrls: [
        'home.scss'
    ]
})
export class HomeComponent implements OnInit {
    private resourceUrl =  SERVER_API_URL + 'api/affectations';
    account: Account;
    modalRef: NgbModalRef;
    affectations: Affectation [];
    AllBuildings: Building [];
    itemsPerPage: any;
    page: any;
    Description: boolean;
    reverse: any;
    predicate: any;
    constructor(
        private jhiAlertService: JhiAlertService,
        private principal: Principal,
        private loginModalService: LoginModalService,
        private eventManager: JhiEventManager,
        private http: HttpClient,

) {
    }
    ngOnInit() {
        this.refresh();
        this.principal.identity().then((account) => {
            this.account = account;
        });
        this.registerAuthenticationSuccess();
    }
    registerAuthenticationSuccess() {
        this.eventManager.subscribe('authenticationSuccess', (message) => {
            this.principal.identity().then((account) => {
                this.account = account;
            });
        });
    }
    isAuthenticated() {
        return this.principal.isAuthenticated();
    }
    login() {
        this.modalRef = this.loginModalService.open();
    }
    onSuccess(data) {
        console.log(data);
        for (let i = 0; i < data.length; i++) {
            this.affectations.push(data[i]);
        }
    }
    onError(Error) {
        this.jhiAlertService.error(Error.message, null, null);
    }
    refresh() {
        this.affectations = [];
        console.log('les affectations de l utilisateur courant ');
        this.http.get('api/affectations/own').subscribe(
            (res: HttpResponse<Affectation[]>) => this.onSuccess(res),
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }
}
