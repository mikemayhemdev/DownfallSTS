package guardian.relics;

import basemod.abstracts.CustomRelic;
import champ.ChampMod;
import champ.relics.ChampionCrown;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.stances.AbstractStance;
import guardian.GuardianMod;
import guardian.cards.GearUp;
import guardian.powers.EnergizedGuardianPower;
import guardian.stances.DefensiveMode;

public class ModeShifterPlus extends CustomRelic {
    public static final String ID = "Guardian:ModeShifterPlus";
    public static final String IMG_PATH = "relics/guardianGear.png";
    public static final String OUTLINE_IMG_PATH = "relics/guardianGearOutline.png";

    public ModeShifterPlus() {
        super(ID, new Texture(GuardianMod.getResourcePath(IMG_PATH)),
                new Texture(GuardianMod.getResourcePath(OUTLINE_IMG_PATH)),
                RelicTier.BOSS, LandingSound.FLAT);
    }

    @Override
    public void atBattleStart() {
        flash();
        addToTop(new MakeTempCardInHandAction(new GearUp()));
        addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
    }

    //finally got around to doing this
    @Override
    public void onTrigger() {
        super.onTrigger();

        addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));

        if (!AbstractDungeon.actionManager.turnHasEnded) {
            addToTop(new GainEnergyAction(1));
        }

        if (AbstractDungeon.actionManager.turnHasEnded) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new EnergizedGuardianPower(AbstractDungeon.player, 1)));
        }

        addToTop(new DrawCardAction(AbstractDungeon.player, 2));
    }

    @Override
    public void obtain() {
        if (AbstractDungeon.player.hasRelic(ModeShifter.ID)) {
            for (int i = 0; i < AbstractDungeon.player.relics.size(); ++i) {
                if (AbstractDungeon.player.relics.get(i).relicId.equals(ModeShifter.ID)) {
                    instantObtain(AbstractDungeon.player, i, true);
                    break;
                }
            }
        } else {
            super.obtain();
        }
    }

    @Override
    public String getUpdatedDescription() {
        // Colorize the starter relic's name
        String name = new ModeShifter().name;
        StringBuilder sb = new StringBuilder();
        if(Settings.language== Settings.GameLanguage.ZHS|| Settings.language== Settings.GameLanguage.ZHT){
            sb.append("[#").append(GuardianMod.mainGuardianColor.toString()).append("]").append(name).append("[]");

        }else {
            for (String word : name.split(" ")) {
                sb.append("[#").append(GuardianMod.mainGuardianColor.toString()).append("]").append(word).append("[] ");
            }
            sb.setLength(sb.length() - 1);
            sb.append("[#").append(GuardianMod.mainGuardianColor.toString()).append("]");
        }

        return DESCRIPTIONS[0] + sb + DESCRIPTIONS[1];
    }


    @Override
    public AbstractRelic makeCopy() {
        return new ModeShifterPlus();
    }

    @Override
    public boolean canSpawn() {
        return AbstractDungeon.player.hasRelic(ModeShifter.ID);
    }
}
