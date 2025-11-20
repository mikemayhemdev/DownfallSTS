package theHexaghost.cards;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.downfallMod;
import theHexaghost.HexaMod;
import theHexaghost.util.HexaPurpleTextInterface;

public class NightmareStrike extends AbstractHexaCard implements HexaPurpleTextInterface {

    public final static String ID = makeID("NightmareStrike");

    public NightmareStrike() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = 4;
        isEthereal = true;
        cardsToPreview = new ShadowStrike();
        tags.add(CardTags.STRIKE);
        tags.add(HexaMod.AFTERLIFE);
        HexaMod.loadJokeCardImage(this, "NightmareStrike.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, makeInfo(), AbstractGameAction.AttackEffect.SLASH_DIAGONAL);
        superFlash(Color.PURPLE);
        AbstractCard q = new ShadowStrike(this);
        if (upgraded) q.upgrade();
        atb(new MakeTempCardInHandAction(q));
    }

    @Override
    public void afterlife() {
        AbstractCard q = new ShadowStrike(this);
        if (upgraded) q.upgrade();
        atb(new MakeTempCardInHandAction(q));
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.cardsToPreview.upgrade();
            upgradeDamage(1);
            rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
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
        String afterlife_name = downfallMod.keywords_and_proper_names.get("afterlife");
        this.keywords.add(afterlife_name);
    }
}