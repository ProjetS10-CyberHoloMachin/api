/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { MicroTestModule } from '../../../test.module';
import { BuildingDataComponent } from '../../../../../../main/webapp/app/entities/building-data/building-data.component';
import { BuildingDataService } from '../../../../../../main/webapp/app/entities/building-data/building-data.service';
import { BuildingData } from '../../../../../../main/webapp/app/entities/building-data/building-data.model';

describe('Component Tests', () => {

    describe('BuildingData Management Component', () => {
        let comp: BuildingDataComponent;
        let fixture: ComponentFixture<BuildingDataComponent>;
        let service: BuildingDataService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [MicroTestModule],
                declarations: [BuildingDataComponent],
                providers: [
                    BuildingDataService
                ]
            })
            .overrideTemplate(BuildingDataComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BuildingDataComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BuildingDataService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new BuildingData(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.buildingData[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
