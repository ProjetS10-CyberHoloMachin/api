import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { BuildingDataDefinition } from './building-data-definition.model';
import { BuildingDataDefinitionPopupService } from './building-data-definition-popup.service';
import { BuildingDataDefinitionService } from './building-data-definition.service';

@Component({
    selector: 'jhi-building-data-definition-delete-dialog',
    templateUrl: './building-data-definition-delete-dialog.component.html'
})
export class BuildingDataDefinitionDeleteDialogComponent {

    buildingDataDefinition: BuildingDataDefinition;

    constructor(
        private buildingDataDefinitionService: BuildingDataDefinitionService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.buildingDataDefinitionService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'buildingDataDefinitionListModification',
                content: 'Deleted an buildingDataDefinition'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-building-data-definition-delete-popup',
    template: ''
})
export class BuildingDataDefinitionDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private buildingDataDefinitionPopupService: BuildingDataDefinitionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.buildingDataDefinitionPopupService
                .open(BuildingDataDefinitionDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
