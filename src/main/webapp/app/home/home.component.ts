import { Component, OnInit } from '@angular/core';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';
import { HttpClient} from '@angular/common/http';

import { Account, LoginModalService, Principal } from '../shared';
import { ActivatedRoute, Router } from '@angular/router';
import { Building } from '../entities/building/building.model';
import { BuildingService } from '../entities/building/building.service';
import { BuildingComponent } from '../entities/building/building.component';
@Component({
    selector: 'jhi-home',
    templateUrl: './home.component.html',
    styleUrls: [
        'home.scss'
    ]

})
export class HomeComponent implements OnInit {
    account: Account;
    modalRef: NgbModalRef;
    buildingList: Building[];
    itemsPerPage: any;
    page: any;
    Description: boolean;
    reverse: any;
    predicate: any;
    constructor(
        private principal: Principal,
        private loginModalService: LoginModalService,
        private eventManager: JhiEventManager,
        private http: HttpClient,
        private buildingComponent: BuildingComponent
    ) {
    }

    ngOnInit() {
        alert('hi there');
        // this.refresh();
        this.principal.identity().then((account) => {
            this.account = account;
        });
        console.log('dans ngOnInit');
        this.registerAuthenticationSuccess();
        this.buildingComponent.loadAll();
        this.buildingList.concat(this.buildingComponent.buildings);
        this.predicate = this.buildingComponent.predicate;
        this.reverse = this.buildingComponent.reverse;
        console.log('le contenu des buildings');
        console.log(this.buildingList);
    }

   /* refresh() {
        this.updatingBuilding = true;
        console.log('hello, right here ');
        this.buildinList = this.http.get('/api/buildings') ;
        console.log(this.buildinList);
        this.updatingBuilding = false;
    }*/

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

}
