# Software Development: How to do it right in the first place?

## Requirement Statements
- Patients in an intensive-care ward in a hospital are monitored by electronic analog devices attached to their bodies by sensors of various kinds.
- Through the sensors the devices measure the patients’ vital factors: one device measure pulse rate, another blood pressure, another temperature, and so on.
- A program is needed to read the factors, at a frequency specified for each patient, and store them in a database.
- The factors read are to be compared with safe ranges specified for each patient, and readings that exceed the safe ranges are to be reported by alarm messages displayed on screen of the nurses station. An alarm message is also to be displayed if any analog device falls.

## SPEC - Input

{monitor_period}
patient {patient_name} {patient_period}
// the following input is 5 parameters in one line separated with space
{device_category} {device_name} {factor_dataset_file} {safe_range_lower_bound} {safe_range_upper_bound}
/* the {device_name} is attached to {patient_name} */

# SPEC - factorDataset format

value // A factor value that should be read by device
... // many many lines

## SPEC - factorDatabase format

/*
factorDatabase must be shown with following rules:
1. patient should be shown with the sequential order from input
2. device should be shown with the sequential order from input
*/

patient {patient_name}
{device_category} {device_name}
{[millisecond from system start to monitor]} {read_factor_value}
...

## SPEC - Output

/*
If two alarm messages are to appear on the same time stamp, they should be displayed in the order of which patient and device were inputted first.
*/

// the following output is in one line separated with space
{[millisecond from system start to monitor]} {patient_name} is in danger! Cause: {device_name} {out_of_range_value}
/* if read factor exceed the safe range */

// the following output is in one line separated with space
{[millisecond from system start to monitor]} {decive_name} falls
/* if factor read from device is -1 or end-of-file, it means device falls */

...

display factorDatabase
/* You must show factorDatabase content after system finish monitoring. The factorDatabase contents would display at bottom of output */

## SPEC - Comment

The first data read from factorDataset should timestamp 0.
The unit of {monitor_period} is millisecond.
The unit of {patient_period} is millisecond.
Both {safe_range_lower_bound} and {safe_range_upper_bound} are inclusive.

device_category: PulseSensor, BloodPressureSensor, TemperatureSensor

You should read the input and factorDataset from file.
And show output to standard output.

After you reach end-of-file in input file, system starts to monitor.
While system starts to monitor, the timestamp is 0.
System finish monitoring when the timestamp reach {monitor_period}.
All devices attached to patients start to measure the patients' vital factor at timestamp 0.

You can use for-loop counter as millisecond timestamp.

There will be one value each line in the factorDataset file.
If you read -1 or end-of-file, it means device falls.
If device falls, the value stored in database is -1.

You are asked to write a main function in Class Main.
We'll test your program through "java Main inputFile"
e.g. java Main sampleInput

## DevLog

1. Parse command line arguments in Java
2. Read/Write files in Java
3. Convert string to int in Java [StackOverflow](https://stackoverflow.com/questions/6881458/converting-a-string-array-into-an-int-array-in-java)

