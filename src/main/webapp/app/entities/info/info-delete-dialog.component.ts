import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Info } from './info.model';
import { InfoPopupService } from './info-popup.service';
import { InfoService } from './info.service';

@Component({
    selector: 'jhi-info-delete-dialog',
    templateUrl: './info-delete-dialog.component.html'
})
export class InfoDeleteDialogComponent {

    info: Info;

    constructor(
        private infoService: InfoService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.infoService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'infoListModification',
                content: 'Deleted an info'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-info-delete-popup',
    template: ''
})
export class InfoDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private infoPopupService: InfoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.infoPopupService
                .open(InfoDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
