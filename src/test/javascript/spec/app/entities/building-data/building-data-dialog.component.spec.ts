/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { MicroTestModule } from '../../../test.module';
import { BuildingDataDialogComponent } from '../../../../../../main/webapp/app/entities/building-data/building-data-dialog.component';
import { BuildingDataService } from '../../../../../../main/webapp/app/entities/building-data/building-data.service';
import { BuildingData } from '../../../../../../main/webapp/app/entities/building-data/building-data.model';
import { BuildingService } from '../../../../../../main/webapp/app/entities/building';
import { BuildingDataDefinitionService } from '../../../../../../main/webapp/app/entities/building-data-definition';

describe('Component Tests', () => {

    describe('BuildingData Management Dialog Component', () => {
        let comp: BuildingDataDialogComponent;
        let fixture: ComponentFixture<BuildingDataDialogComponent>;
        let service: BuildingDataService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [MicroTestModule],
                declarations: [BuildingDataDialogComponent],
                providers: [
                    BuildingService,
                    BuildingDataDefinitionService,
                    BuildingDataService
                ]
            })
            .overrideTemplate(BuildingDataDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BuildingDataDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BuildingDataService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new BuildingData(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.buildingData = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'buildingDataListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new BuildingData();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.buildingData = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'buildingDataListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
