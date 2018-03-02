import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { InfoDefinition } from './info-definition.model';
import { InfoDefinitionService } from './info-definition.service';

@Component({
    selector: 'jhi-info-definition-detail',
    templateUrl: './info-definition-detail.component.html'
})
export class InfoDefinitionDetailComponent implements OnInit, OnDestroy {

    infoDefinition: InfoDefinition;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private infoDefinitionService: InfoDefinitionService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInInfoDefinitions();
    }

    load(id) {
        this.infoDefinitionService.find(id)
            .subscribe((infoDefinitionResponse: HttpResponse<InfoDefinition>) => {
                this.infoDefinition = infoDefinitionResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInInfoDefinitions() {
        this.eventSubscriber = this.eventManager.subscribe(
            'infoDefinitionListModification',
            (response) => this.load(this.infoDefinition.id)
        );
    }
}
