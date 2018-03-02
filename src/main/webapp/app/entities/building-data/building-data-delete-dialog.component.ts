import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { BuildingData } from './building-data.model';
import { BuildingDataPopupService } from './building-data-popup.service';
import { BuildingDataService } from './building-data.service';

@Component({
    selector: 'jhi-building-data-delete-dialog',
    templateUrl: './building-data-delete-dialog.component.html'
})
export class BuildingDataDeleteDialogComponent {

    buildingData: BuildingData;

    constructor(
        private buildingDataService: BuildingDataService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.buildingDataService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'buildingDataListModification',
                content: 'Deleted an buildingData'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-building-data-delete-popup',
    template: ''
})
export class BuildingDataDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private buildingDataPopupService: BuildingDataPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.buildingDataPopupService
                .open(BuildingDataDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
