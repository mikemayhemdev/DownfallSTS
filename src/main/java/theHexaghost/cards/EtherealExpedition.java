package theHexaghost.cards;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.downfallMod;
import theHexaghost.HexaMod;
import theHexaghost.util.HexaPurpleTextInterface;

import java.util.ArrayList;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.cardRandomRng;

public class EtherealExpedition extends AbstractHexaCard implements HexaPurpleTextInterface {

    public final static String ID = makeID("EtherealExpedition");

    //eerie expedition

    public EtherealExpedition() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        isEthereal = true;
        tags.add(HexaMod.AFTERLIFE);
        exhaust = false;
        baseMagicNumber = magicNumber = 1;
        HexaMod.loadJokeCardImage(this, "EtherealExpedition.png");
    }

    public static AbstractCard returnTrulyRandomEtherealCardInCombat() {
        ArrayList<AbstractCard> list = new ArrayList<>();// 1201
        for (AbstractCard c : AbstractDungeon.srcCommonCardPool.group) {
            if (c.hasTag(HexaMod.AFTERLIFE) && !c.hasTag(CardTags.HEALING)) {
                list.add(c);
            }
        }

        for (AbstractCard c : AbstractDungeon.srcUncommonCardPool.group) {
            if (c.hasTag(HexaMod.AFTERLIFE) && !c.hasTag(CardTags.HEALING)) {
                list.add(c);
            }
        }
        for (AbstractCard c : AbstractDungeon.srcRareCardPool.group) {
            if (c.hasTag(HexaMod.AFTERLIFE) && !c.hasTag(CardTags.HEALING)) {
                list.add(c);
            }
        }
        if (list.isEmpty()) {
            //Since this card can show up on Snecko, and Snecko obviously doesn't have any afterlife
            //cards in their pool, ALL of these cards need to be added here, and not just Power from Beyond.
            list.add(new Hexaguard());
            list.add(new NightmareGuise());
            list.add(new NightmareStrike());
            list.add(new SpectersWail());
            list.add(new BurningQuestion());
            list.add(new EtherealExpedition());
            list.add(new FlamesFromBeyond());
            list.add(new Floatwork());
            list.add(new GhostLash());
            list.add(new GhostShield());
            list.add(new Haunt());
            list.add(new PowerFromBeyond());
            list.add(new EtherStep());
        }
        return list.get(cardRandomRng.random(list.size() - 1));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {

        for (int i = 0; i < this.magicNumber; i++) {
            AbstractCard q = returnTrulyRandomEtherealCardInCombat().makeCopy();
            addToBot(new MakeTempCardInHandAction(q));
        }
        for (int i = 0; i < 1; i++) {
            AbstractCard q = returnTrulyRandomEtherealCardInCombat().makeCopy();
            addToBot(new MakeTempCardInDrawPileAction(q, 1, true, true));
        }
//        q.setCostForTurn(0);
//        this.addToBot(new MakeTempCardInHandAction(q, true));// 34
    }

    public void afterlife() {
        for (int i = 0; i < 1; i++) {
            AbstractCard q = returnTrulyRandomEtherealCardInCombat().makeCopy();
            addToBot(new MakeTempCardInDrawPileAction(q, 1, true, true));
        }
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(0);
        }
    }


    // to still show afterlife tooltip. because the format [purple]hexamod:afterlife[] doesnt get displayed correctly
    // we are only using [purple]afterlife[] here for easier text comprehension for new players, but doing this
    // means we dont have the keyword tooltip so we need to manually add it
    // but after I tried adding it in the constrcutor it turns out sometimes who knows why it wont be added
    // and this way seems to work
    @Override
    public void initializeDescription() {
        super.initializeDescription();
        if(Settings.language != Settings.GameLanguage.ZHS && Settings.language != Settings.GameLanguage.ZHT) {
            this.keywords.add(downfallMod.keywords_and_proper_names.get("afterlife"));
        }
    }
}