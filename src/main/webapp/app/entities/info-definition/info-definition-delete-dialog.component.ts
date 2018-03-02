import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { InfoDefinition } from './info-definition.model';
import { InfoDefinitionPopupService } from './info-definition-popup.service';
import { InfoDefinitionService } from './info-definition.service';

@Component({
    selector: 'jhi-info-definition-delete-dialog',
    templateUrl: './info-definition-delete-dialog.component.html'
})
export class InfoDefinitionDeleteDialogComponent {

    infoDefinition: InfoDefinition;

    constructor(
        private infoDefinitionService: InfoDefinitionService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.infoDefinitionService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'infoDefinitionListModification',
                content: 'Deleted an infoDefinition'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-info-definition-delete-popup',
    template: ''
})
export class InfoDefinitionDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private infoDefinitionPopupService: InfoDefinitionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.infoDefinitionPopupService
                .open(InfoDefinitionDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
