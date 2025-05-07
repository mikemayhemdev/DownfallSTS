package champ.relics;

import automaton.AutomatonMod;
import automaton.relics.BronzeCore;
import basemod.abstracts.CustomRelic;
import champ.ChampMod;
import champ.actions.OpenerReduceCostAction;
import champ.powers.UltimateFormPower;
import champ.stances.UltimateStance;
import champ.util.OnOpenerSubscriber;
import com.megacrit.cardcrawl.core.Settings;
import downfall.util.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import slimebound.relics.AbsorbEndCombat;

import static champ.ChampMod.makeRelicOutlinePath;
import static champ.ChampMod.makeRelicPath;

public class ChampionCrownUpgraded extends CustomRelic {

    public static final String ID = ChampMod.makeID("ChampionCrownUpgraded");
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("UltimateChampionCrown.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("ChampionCrown.png"));


    public ChampionCrownUpgraded() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.MAGICAL);
    }

    //todo: rework this relic

    //# of turns that ultimate stance lasts for
    private static final int AMOUNT = 3;

    @Override
    public void atBattleStart() {
        addToBot(new ChangeStanceAction(UltimateStance.STANCE_ID));
        addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new UltimateFormPower(AMOUNT), AMOUNT));
    }

    @Override
    public void obtain() {
        if (AbstractDungeon.player.hasRelic(ChampionCrown.ID)) {
            for (int i = 0; i < AbstractDungeon.player.relics.size(); ++i) {
                if (AbstractDungeon.player.relics.get(i).relicId.equals(ChampionCrown.ID)) {
                    instantObtain(AbstractDungeon.player, i, true);
                    break;
                }
            }
        } else {
            super.obtain();
        }
    }

    public boolean canSpawn() {
        return AbstractDungeon.player.hasRelic(ChampionCrown.ID);
    }

    @Override
    public String getUpdatedDescription() {
        // Colorize the starter relic's name
        String name = new ChampionCrown().name;
        StringBuilder sb = new StringBuilder();
        if(Settings.language== Settings.GameLanguage.ZHS|| Settings.language== Settings.GameLanguage.ZHT){
            sb.append("[#").append(ChampMod.placeholderColor.toString()).append("]").append(name).append("[]");

        }else {
            for (String word : name.split(" ")) {
                sb.append("[#").append(ChampMod.placeholderColor.toString()).append("]").append(word).append("[] ");
            }
            sb.setLength(sb.length() - 1);
            sb.append("[#").append(ChampMod.placeholderColor.toString()).append("]");
        }

        return DESCRIPTIONS[0] + sb + DESCRIPTIONS[1] + AMOUNT + DESCRIPTIONS[2];
    }

}
