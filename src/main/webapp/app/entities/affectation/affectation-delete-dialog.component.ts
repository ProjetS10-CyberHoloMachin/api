import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Affectation } from './affectation.model';
import { AffectationPopupService } from './affectation-popup.service';
import { AffectationService } from './affectation.service';

@Component({
    selector: 'jhi-affectation-delete-dialog',
    templateUrl: './affectation-delete-dialog.component.html'
})
export class AffectationDeleteDialogComponent {

    affectation: Affectation;

    constructor(
        private affectationService: AffectationService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.affectationService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'affectationListModification',
                content: 'Deleted an affectation'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-affectation-delete-popup',
    template: ''
})
export class AffectationDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private affectationPopupService: AffectationPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.affectationPopupService
                .open(AffectationDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
