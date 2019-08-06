# Android-Final

პროექტი დაყოფილია 4 ძირითად ნაწილად. model package-ში ინახება ის ბაზაში შენახული Entity ობიექტები, კლასები და ინტერფეისები რომლებიც უზრუნველჰყოფენ ბაზიდან შეტყობინების/ისტორიის ამოღება/ჩაწერას და დამხმარე ობიექტები (Data Transfer Objects) რომელთა მეშვეობითაც გამომძახებელს (View-ს მხარეს) გადაეცემა ბაზის ობიექტებში ჩაწერილი ინფორმაცია. ბაზაში მონაცემების შენახვა გადავწყვიტე შემდეგნაირად. თითოეულ ტელეფონთან მიმოწერისთვის ინახება თითო HistoryEntry ობიექტი (ანუ ტელეფონთან მანამდეც თუ ვიყავით დაკავშირებული ახალი კავშირისას ხდება ძველი მიმოწერის ჩატვირთვა) და ForeignKey-ით ებმევა ისტორიის ობიექტს შესაბამისი შეტყობიენბები.
connector package-ში არის ჩაყრილი ყველა ის კლასი რომელიც პასუხისმგებელია კავშირის დამყარებაზე:
P2PBroadcastReceiver - სახელითაც როგორც ხვდებით პასუხისმგებელია WifiP2PManager-თან და WifiManager ურთიერთობაზე, ასევე მიღებული intent-ების დამუშავებაზე.
Connector - არის ჩვეულებრივი ნაკადი რომელსაც გადაეცემა მოწყობილობის მისამართი რომელსაც უნდა დაუკავშირდეს ან თუ თვითონაა ინიციატორი ქმნის სერვერის სოკეტს. ნაკადი სოკეტის შექმნის და კავშირის დაწყების შემდეგ იღებს input და output სტრიმებს და ცდილობს მუდმივად წაიკითხოს input-ის შეტყობინება. როდესაც იხურება სტრიმი ეს კლასი ატყობინებს Controller-ს რომ კავშირი შეწყდა. შეტყობინების ჩAწერისთვისაც ხდება არა Main ნაკადის გამოძახება (threadPool-ია Connector კლასში რომელიც გზავნის გადაცემულ შეტყობინებებს).
Controller-არის კლასი რომელიც აკავშირებს ბაზის და სხვა მოწყობილობასთან კავშირზე პასუხისმგებელ კლასებს View-ს ნაწილთან. როდესაც მომხმარებელი მაგალითად აჭერს ჩატის დაწყების ღილაკს view-დან მოდის მოთხოვნა Controller-თან, რომელიც თავის მხრივ ატყობინებს  P2PBroadcastReceiver-ს რომ კავშირი უნდა დამყარდეს, P2PBroadcastReceiver კლასი კავშირის დამყარების შემდეგ კვლავ იძახებს Controller-ს რომელიც ქოქავს სერვერის/კლიენტის ნაკადებს (Connector კლასი) და კავშირის დამყარების შემდეგ ისევ Controller-ს ატყობინებს რომ კავშირი შედგა. ამის შედეგ Controller იღებს დაკავშირებულ ტელეფონთან არსებულ ძველ ჩატს ბაზასთან კავშირის კლასების მეშვეობით და ბაზის ჩატის ობიექტს (ან ახალს თუ ტელეფონი პირველად დაგვიკავშირდა) შეტყობინებებიანად გადასცემს View-ს ნაწილს. ასევე ამ კლასთან მოდის View-დან გამოგზავნილი ბაზის ცვლიების ბრძანებები (ჩატის წაშლა).
ui პაკეტში არის ძირითადად 2 მნიშვნელოვანი ნაწილი MainActivity რომლის გავლითაც ახდენს ყველა ფრაგმენტი ბაზის და სოკეტის ნაწილებთან დაკავშირებას(ასევე ეს კლასი აკონტროლებს ნავიგაციას)  და თვითონ ფრაგმენტები რომლებიც პასუხისმგებელი არიან მხოლოდ გვერდების დახატვვაზე. როდესაც შეტყობინებას წერს მომხმარებელი fragment-ის კლასებიდან ხდება MainActivity-ს მეთოდის გამოძახება ჩაწერილი შეტყობინების ტექსტით MainActivity იძახებს Controller-ს რომელიც თავის მხრივ Connector-თან აგზავნის შეტყობინების ჩაწერის ბრძანებას, შემდეგ ბაზაზე პასუხისმგებელ კლასებს გადასცემს შეტყობინებას და ბაზაში ჩაწერილი შეტყობინება უკან უბრუნდება Controller-ს (აქედან მიღებული და გაგზავნილი შეტყობინების დახატვის ნაწილი იდენტურია) ბაზაში მოხდა ჩაწერა და Controller იძახებს MainActivity-ს რომელიც შესაბამის ფრაგმენტს გადასცემს ახალ შეტყობნებას გამოსაჩენად. წაკითხული შეტყობინებაც მსგავსად მიდის View-მდე. Connector ნაკადი როდესაც წაიკითხავს ხაზს იძახებს Controller-ს საიდანაც მიდის ზუსტად ის პროცესი რაც წეღან ავღწერე გასაგზავნი შეტყობინებისთვის.
და ბოლო ნაწილი კავშირის დახურვა, ეს პროცესი შეიძლება მოხდეს როგორც MainActivity-ზე back ღილაკის დაჭერისას (როცა ჩატის ფრაგმენტია გახსნილი) ასევე Connector-დან. როდესაც MainActivity-ზე ჩატი იხურება Controller-ში ხდება კავშირის დახურვის ბრძანების გამოძახება, როდესაც Controller იღებს ბრძანებას P2PBroadcastReceiver-ს ეუბნება რომ Connection დასახურია და ხდება wifidirect ჯგუფის წაშლა. ასევე Connector ნაკადი interrupt-დება და იხურება კავშირი. ამის შემდეგ მისდის MainActivity-ს შეტყობინება რომ ჩატი დაიხურა და ხდება ისტორიის გვერდის გახსნა. მეორე შემთხვევაში კავშირი იხურება იმ დროს როდესაც Connector ნაკადი input სტრიმის კითხვისას იგებს რომ სტრიმი დაიხურა ამ დროს პირდაპირ ხდება Controller-ის იმ მეთოდის გამოძახება რომელსაც MainActivity იძახებდა და პროცესი ერთი ერთში მეორდება Controller გადასცემს დახურვის ბრძანებას ისევ Connector-ს, P2PBroadcastReceiver-ს და წარმატების შემთხვევაში ხდება MainActivity-ზე გვერდის შეცვლა.
