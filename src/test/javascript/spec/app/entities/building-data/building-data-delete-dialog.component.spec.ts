/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { MicroTestModule } from '../../../test.module';
import { BuildingDataDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/building-data/building-data-delete-dialog.component';
import { BuildingDataService } from '../../../../../../main/webapp/app/entities/building-data/building-data.service';

describe('Component Tests', () => {

    describe('BuildingData Management Delete Component', () => {
        let comp: BuildingDataDeleteDialogComponent;
        let fixture: ComponentFixture<BuildingDataDeleteDialogComponent>;
        let service: BuildingDataService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [MicroTestModule],
                declarations: [BuildingDataDeleteDialogComponent],
                providers: [
                    BuildingDataService
                ]
            })
            .overrideTemplate(BuildingDataDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BuildingDataDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BuildingDataService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(Observable.of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
