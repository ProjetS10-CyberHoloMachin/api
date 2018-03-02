import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { BuildingDataDefinition } from './building-data-definition.model';
import { BuildingDataDefinitionService } from './building-data-definition.service';

@Injectable()
export class BuildingDataDefinitionPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private buildingDataDefinitionService: BuildingDataDefinitionService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.buildingDataDefinitionService.find(id)
                    .subscribe((buildingDataDefinitionResponse: HttpResponse<BuildingDataDefinition>) => {
                        const buildingDataDefinition: BuildingDataDefinition = buildingDataDefinitionResponse.body;
                        this.ngbModalRef = this.buildingDataDefinitionModalRef(component, buildingDataDefinition);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.buildingDataDefinitionModalRef(component, new BuildingDataDefinition());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    buildingDataDefinitionModalRef(component: Component, buildingDataDefinition: BuildingDataDefinition): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.buildingDataDefinition = buildingDataDefinition;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
