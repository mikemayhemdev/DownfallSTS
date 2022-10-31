/*package slimebound.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.exordium.GoopPuddle;
import com.megacrit.cardcrawl.events.exordium.ScrapOoze;
import slimebound.SlimeboundMod;
import slimebound.characters.SlimeboundCharacter;
import slimebound.events.ArtOfSlimeWar;
import slimebound.events.Hunted;
import slimebound.events.WorldOfGoopSlimebound;
import slimebound.relics.GreedOozeRelic;
import slimebound.relics.ScrapOozeRelic;
import expansioncontent.relics.StudyCardRelic;

@SpirePatch(clz = AbstractDungeon.class, method = "initializeCardPools")
public class CityRemoveEventPatch {

    public static void Prefix(AbstractDungeon dungeon_instance) {
        if (AbstractDungeon.player instanceof SlimeboundCharacter) {
            dungeon_instance.eventList.remove(GoopPuddle.ID);
        } else {
            dungeon_instance.eventList.remove(WorldOfGoopSlimebound.ID);

        }

        if (AbstractDungeon.player.hasRelic(ScrapOozeRelic.ID)) {
            dungeon_instance.eventList.remove(ScrapOoze.ID);
        }
        if (AbstractDungeon.player.hasRelic(GreedOozeRelic.ID)) {
            dungeon_instance.eventList.remove(WorldOfGoopSlimebound.ID);
            dungeon_instance.eventList.remove(GoopPuddle.ID);
        }


        if (AbstractDungeon.player instanceof SlimeboundCharacter) {
            SlimeboundCharacter sc = (SlimeboundCharacter) AbstractDungeon.player;
            if (sc.foughtSlimeBoss || sc.hasRelic(StudyCardRelic.ID)) {
                dungeon_instance.eventList.remove(Hunted.ID);
            }
        } else {
            dungeon_instance.eventList.remove(Hunted.ID);
        }


        if (!SlimeboundMod.contentSharing_events) {
            if (!(AbstractDungeon.player instanceof SlimeboundCharacter)) {
                dungeon_instance.eventList.remove(ArtOfSlimeWar.ID);
            }
        }


        ////SlimeboundMod.logger.info("Removed Hunted event from pool.");

    }
}*/