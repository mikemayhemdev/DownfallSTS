package hermit.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.colorless.Trip;
import com.megacrit.cardcrawl.cards.curses.CurseOfTheBell;
import com.megacrit.cardcrawl.cards.curses.Injury;
import com.megacrit.cardcrawl.cards.green.Tactician;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.*;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.GainPennyEffect;
import hermit.HermitMod;
import hermit.cards.MementoCard;
import hermit.cards.Strike_Hermit;
import hermit.util.TextureLoader;

import java.util.Iterator;

import static hermit.HermitMod.makeRelicOutlinePath;
import static hermit.HermitMod.makeRelicPath;


public class ClaspedLocket extends CustomRelic {

    // ID, images, text.
    public static final String ID = HermitMod.makeID("ClaspedLocket");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("clasped_locket.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("clasped_locket.png"));

    public int TURNS = 0;
    public static int increaseGold = 75;

    private boolean cardsReceived = true;
    private boolean firstTurn = false;

    public ClaspedLocket() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.FLAT);
    }


    @Override
    public void obtain() {
        if (AbstractDungeon.player.hasRelic(Memento.ID)) {
            for (int i = 0; i < AbstractDungeon.player.relics.size(); ++i) {
                if (AbstractDungeon.player.relics.get(i).relicId.equals(Memento.ID)) {
                    instantObtain(AbstractDungeon.player, i, true);
                    break;
                }
            }
        } else { super.obtain(); }

        this.cardsReceived = false;
    }

    @Override
    public void onEquip() {
        this.counter = 0;
        Iterator var1 = AbstractDungeon.player.masterDeck.group.iterator();

        while(var1.hasNext()) {
            AbstractCard c = (AbstractCard)var1.next();
            if (c.type == AbstractCard.CardType.CURSE) {
                ++this.counter;
            }
        }
    }


    public void onMasterDeckChange() {
        this.counter = 0;
        Iterator var1 = AbstractDungeon.player.masterDeck.group.iterator();

        while(var1.hasNext()) {
            AbstractCard c = (AbstractCard)var1.next();
            if (c.type == AbstractCard.CardType.CURSE) {
                ++this.counter;
            }
        }
    }

    public boolean canSpawn() {
        return AbstractDungeon.player.hasRelic(Memento.ID);
    }

    public void atPreBattle() {
        this.firstTurn = true;
    }

    public void atTurnStart() {
        if (this.firstTurn) {
            this.flash();
            this.addToTop(new GainEnergyAction(this.counter));
            this.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            this.firstTurn = false;
        }

    }

    public void update() {
        super.update();

        if (!cardsReceived && !AbstractDungeon.isScreenUp && isObtained) {
            cardsReceived = true;
            CardCrawlGame.sound.play("NECRONOMICON");
            CardGroup curseaddgroup = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            for (int aaa = 0; aaa < 2; aaa++) {
                AbstractCard InjuryCard = new Injury();
                curseaddgroup.addToBottom(InjuryCard.makeCopy());
            }
            AbstractDungeon.gridSelectScreen.openConfirmationGrid(curseaddgroup, this.DESCRIPTIONS[1]);
        }
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
