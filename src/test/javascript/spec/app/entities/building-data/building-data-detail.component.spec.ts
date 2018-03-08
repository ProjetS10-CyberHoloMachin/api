/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { CyberholocampusTestModule } from '../../../test.module';
import { BuildingDataDetailComponent } from '../../../../../../main/webapp/app/entities/building-data/building-data-detail.component';
import { BuildingDataService } from '../../../../../../main/webapp/app/entities/building-data/building-data.service';
import { BuildingData } from '../../../../../../main/webapp/app/entities/building-data/building-data.model';

describe('Component Tests', () => {

    describe('BuildingData Management Detail Component', () => {
        let comp: BuildingDataDetailComponent;
        let fixture: ComponentFixture<BuildingDataDetailComponent>;
        let service: BuildingDataService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [CyberholocampusTestModule],
                declarations: [BuildingDataDetailComponent],
                providers: [
                    BuildingDataService
                ]
            })
            .overrideTemplate(BuildingDataDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BuildingDataDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BuildingDataService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new BuildingData(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.buildingData).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
