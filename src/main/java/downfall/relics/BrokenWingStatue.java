package downfall.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.EscapeAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.city.Chosen;
import com.megacrit.cardcrawl.monsters.exordium.Cultist;
import downfall.downfallMod;
import downfall.actions.LoseRelicAction;
import downfall.actions.SpeechBubbleAction;
import downfall.events.WingStatue_Evil;
import slimebound.SlimeboundMod;

public class BrokenWingStatue extends CustomRelic {

    public static final String ID = downfallMod.makeID("BrokenWingStatue");
    private static final Texture IMG = new Texture(downfallMod.assetPath("images/relics/WingStatue.png"));
    private static final Texture OUTLINE = new Texture(downfallMod.assetPath("images/relics/Outline/WingStatue.png"));

    private static final String[] DIALOG = CardCrawlGame.languagePack.getEventString(WingStatue_Evil.ID).DESCRIPTIONS;

    public static boolean GIVEN = false;

    private AbstractMonster receiver;

    public BrokenWingStatue() {
        super(ID, IMG, OUTLINE, RelicTier.SPECIAL, LandingSound.FLAT);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void atBattleStartPreDraw() {
        this.receiver = null;
        for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
            if (m instanceof Cultist || m instanceof Chosen) {
                this.receiver = m;
                //SlimeboundMod.logger.info("found valid target");
                break;
            }
        }
        if (this.receiver != null) {
            GIVEN = true;
            int DialogIndex;
            if (this.receiver instanceof Cultist) {
                DialogIndex = 4;
                //SlimeboundMod.logger.info("assigned dialog index 4");
            } else {
                DialogIndex = 6;
                //SlimeboundMod.logger.info("assigned dialog index 6");
            }
            this.flash();
            forceWait(5);
            addToBot(new RelicAboveCreatureAction(this.receiver, this));
            addToBot(new SpeechBubbleAction(DIALOG[DialogIndex], this.receiver, 2F));
            forceWait(12);
            this.flash();
            addToBot(new RelicAboveCreatureAction(this.receiver, this));
            addToBot(new SpeechBubbleAction(DIALOG[DialogIndex + 1], this.receiver, 2F));
            forceWait(7);
            this.flash();
            addToBot(new RelicAboveCreatureAction(this.receiver, this));
            AbstractDungeon.actionManager.addToBottom(new LoseRelicAction(this.relicId));
            AbstractDungeon.actionManager.addToBottom(new EscapeAction(this.receiver));

        }
    }

    private void forceWait(int num) {
        for (int i = 0; i < num; i++) {

            addToBot(new WaitAction(0.1F));
        }
    }

}
