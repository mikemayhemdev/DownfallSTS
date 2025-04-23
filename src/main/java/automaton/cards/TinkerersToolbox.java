package automaton.cards;

import automaton.AutomatonMod;
import basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard.MultiCardPreview;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.tempCards.Shiv;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.util.SelectCardsCenteredAction;
import gremlin.cards.Ward;
import sneckomod.SneckoMod;

import static automaton.AutomatonMod.makeBetaCardPath;

import java.util.ArrayList;

public class TinkerersToolbox extends AbstractBronzeCard {

    public final static String ID = makeID("TinkerersToolbox");

    //stupid intellij stuff skill, self, rare

    private float rotationTimer;
    private int previewIndex;
    private ArrayList<AbstractCard> cardsList = new ArrayList<>();

    public TinkerersToolbox() {
        super(ID, 1, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        exhaust = true;
        selfRetain = true;
       // this.tags.add(SneckoMod.BANNEDFORSNECKO);
        cardsList.add(new Debug());
        cardsList.add(new Batch());
        cardsList.add(new Decompile());
        cardsList.add(new ByteShift());
        MultiCardPreview.add(new Debug(), new Batch(), new Decompile(), new ByteShift());
        AutomatonMod.loadJokeCardImage(this, makeBetaCardPath("TinkerersToolbox.png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SelectCardsCenteredAction(cardsList, 1, masterUI.TEXT[8], (cards) -> addToTop(new MakeTempCardInHandAction(cards.get(0).makeCopy(), true))));
    }

    public void upp() {
       upgradeBaseCost(0);
    }



    }