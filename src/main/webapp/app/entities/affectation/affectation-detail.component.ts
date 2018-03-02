import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Affectation } from './affectation.model';
import { AffectationService } from './affectation.service';

@Component({
    selector: 'jhi-affectation-detail',
    templateUrl: './affectation-detail.component.html'
})
export class AffectationDetailComponent implements OnInit, OnDestroy {

    affectation: Affectation;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private affectationService: AffectationService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInAffectations();
    }

    load(id) {
        this.affectationService.find(id)
            .subscribe((affectationResponse: HttpResponse<Affectation>) => {
                this.affectation = affectationResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInAffectations() {
        this.eventSubscriber = this.eventManager.subscribe(
            'affectationListModification',
            (response) => this.load(this.affectation.id)
        );
    }
}
