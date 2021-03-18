package guardian.cards;

import automaton.FunctionHelper;
import basemod.abstracts.CustomCard;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import guardian.GuardianMod;
import guardian.actions.BraceAction;
import guardian.powers.BeamBuffPower;
import guardian.powers.ModeShiftPower;
import guardian.relics.DefensiveModeMoreBlock;

import java.util.ArrayList;


public abstract class AbstractGuardianCard extends CustomCard {
    public String betaArtPath;
    public Integer socketCount = 0;
    public ArrayList<GuardianMod.socketTypes> sockets = new ArrayList<>();
    public GuardianMod.socketTypes thisGemsType = null;

    public int multihit;
    public int upgradeMulthit;
    public boolean isMultihitModified;

    public int secondaryM;
    public boolean upgradesecondaryM;
    public boolean isSecondaryMModified;

    public boolean flipPreviewMode = false;

    public AbstractGuardianCard(String id, String name, String img, int cost, String rawDescription, CardType type, CardColor color,
                                CardRarity rarity, CardTarget target) {

        super(id, name, img, cost, rawDescription, type,
                color, rarity, target);

        if (AbstractDungeon.player != null) {
            upgradeMultihit();
        }


    }

    @Override
    protected Texture getPortraitImage() {
        if (Settings.PLAYTESTER_ART_MODE || UnlockTracker.betaCardPref.getBoolean(this.cardID, false)) {
            if (this.textureImg == null) {
                return null;
            } else {
                if (betaArtPath != null) {
                    int endingIndex = betaArtPath.lastIndexOf(".");
                    String newPath = betaArtPath.substring(0, endingIndex) + "_p" + betaArtPath.substring(endingIndex);
                    System.out.println("Finding texture: " + newPath);

                    Texture portraitTexture;
                    try {
                        portraitTexture = ImageMaster.loadImage(newPath);
                    } catch (Exception var5) {
                        portraitTexture = null;
                    }

                    return portraitTexture;
                }
            }
        }
        return super.getPortraitImage();
    }

    public void whenReturnedFromStasis() {

    }

    /*

    @Override
    public Type savedType()
    {
        return new TypeToken<ArrayList<Integer>>(){}.getType();
    }

    @Override
    public ArrayList<Integer> onSave()
    {
        if (sockets.size() > 0 || eventSockets > 0){

        ArrayList<Integer> savedSockets = new ArrayList<>();
        savedSockets.add(this.eventSockets);

            for (int i = 1; i < socketCount + 1; i++) {
            if (sockets.size() > i - 1) {
                switch (sockets.get(i - 1)) {
                    case RED:
                        savedSockets.add(0);
                        break;
                    case GREEN:
                        savedSockets.add(1);
                        break;
                    case ORANGE:
                        savedSockets.add(2);
                        break;
                    case WHITE:
                        savedSockets.add(3);
                        break;
                    case CYAN:
                        savedSockets.add(4);
                        break;
                    case BLUE:
                        savedSockets.add(5);
                        break;
                    case CRIMSON:
                        savedSockets.add(6);
                        break;
                    case FRAGMENTED:
                        savedSockets.add(7);
                        break;
                    case PURPLE:
                        savedSockets.add(8);
                        break;
                    case SYNTHETIC:
                        savedSockets.add(9);
                        break;
                    case YELLOW:
                        savedSockets.add(10);
                        break;
                    case LIGHTBLUE:
                        savedSockets.add(11);
                        break;
                }
            }
        }
            return savedSockets;
        } else {
            return null;
        }
    }

    @Override
    public void onLoad(ArrayList<Integer> loadedSockets) {
        if (loadedSockets != null){

            if (loadedSockets.get(0) > 0){
                this.socketCount += loadedSockets.get(0);
            }
        for (int i = 1; i < socketCount + 1; i++) {
            if (loadedSockets.size() > i) {
                switch (loadedSockets.get(i)) {
                    case 0:
                        sockets.add(GuardianMod.socketTypes.RED);
                        break;
                    case 1:
                        sockets.add(GuardianMod.socketTypes.GREEN);
                        break;
                    case 2:
                        sockets.add(GuardianMod.socketTypes.ORANGE);
                        break;
                    case 3:
                        sockets.add(GuardianMod.socketTypes.WHITE);
                        break;
                    case 4:
                        sockets.add(GuardianMod.socketTypes.CYAN);
                        break;
                    case 5:
                        sockets.add(GuardianMod.socketTypes.BLUE);
                        break;
                    case 6:
                        sockets.add(GuardianMod.socketTypes.CRIMSON);
                        break;
                    case 7:
                        sockets.add(GuardianMod.socketTypes.FRAGMENTED);
                        break;
                    case 8:
                        sockets.add(GuardianMod.socketTypes.PURPLE);
                        break;
                    case 9:
                        sockets.add(GuardianMod.socketTypes.SYNTHETIC);
                        break;
                    case 10:
                        sockets.add(GuardianMod.socketTypes.YELLOW);
                        break;
                    case 11:
                        sockets.add(GuardianMod.socketTypes.LIGHTBLUE);
                        break;
                }
            }
        }
        }
        updateDescription();
    }
    */

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        if (this.hasTag(GuardianMod.STASISGLOW)) this.tags.remove(GuardianMod.STASISGLOW);

        GuardianMod.logger.info("New card played: " + this.name + " misc = " + this.misc);
    }

    public static void brace(int modeShiftValue) {
        if (!AbstractDungeon.player.hasPower(ModeShiftPower.POWER_ID)){
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new ModeShiftPower(AbstractDungeon.player, AbstractDungeon.player, 20), 20));
        }
        AbstractDungeon.actionManager.addToBottom(new BraceAction(modeShiftValue));

    }

    public void saveGemMisc() {
        if (AbstractDungeon.player != null) {

            if (AbstractDungeon.player.masterDeck.contains(this)) {

                this.misc = 0;
                this.misc = 10 + this.socketCount;
                GuardianMod.logger.info("New misc gem save in progress: " + this.name + " new misc = " + this.misc);

                if (sockets.size() > 0) {
                    for (int i = 0; i < sockets.size(); i++) {
                        this.misc *= 100;
                        int gemindex = 0;
                        switch (sockets.get(i)) {
                            case RED:
                                gemindex = 0;
                                break;
                            case GREEN:
                                gemindex = 1;
                                break;
                            case ORANGE:
                                gemindex = 2;
                                break;
                            case WHITE:
                                gemindex = 3;
                                break;
                            case CYAN:
                                gemindex = 4;
                                break;
                            case BLUE:
                                gemindex = 5;
                                break;
                            case CRIMSON:
                                gemindex = 6;
                                break;
                            case FRAGMENTED:
                                gemindex = 7;
                                break;
                            case PURPLE:
                                gemindex = 8;
                                break;
                            case SYNTHETIC:
                                gemindex = 9;
                                break;
                            case YELLOW:
                                gemindex = 10;
                                break;
                            case LIGHTBLUE:
                                gemindex = 11;
                                break;
                        }
                        this.misc += 10 + gemindex;
                        GuardianMod.logger.info("New misc gem save in progress: " + this.name + " new misc = " + this.misc);

                    }
                }
                GuardianMod.logger.info("New misc gem save final: " + this.name + " new misc = " + this.misc);

            }
        }
    }

    public void loadGemMisc() {
        this.sockets.clear();
        if (this.misc > 0) {
            GuardianMod.logger.info("Attempting to load non-zero misc " + this.misc);
            if (Integer.toString(this.misc).length() % 2 == 0) {
                GuardianMod.logger.info("New misc gem load: " + this.name + " new misc = " + this.misc);
                String miscString = Integer.toString(this.misc);
                String socketCountString = miscString.substring(0, 2);
                GuardianMod.logger.info("New misc gem load: " + this.name + " socket string = " + socketCountString + " parsed = " + Integer.parseInt(socketCountString));

                this.socketCount = Integer.parseInt(socketCountString) - 10;
                GuardianMod.logger.info("New misc gem load: " + this.name + " new sockets = " + this.socketCount);

                //SOCKETS
                if (miscString.length() > 2) {
                    miscString = miscString.substring(2);
                    GuardianMod.logger.info("New misc gem load: " + this.name + " new misc = " + miscString);

                } else {
                    this.updateDescription();
                    return;
                }

                //GEMS

                int loops = miscString.length() / 2;
                String gemString;
                for (int i = 0; i < loops; i++) {
                    gemString = miscString.substring(0, 2);
                    switch (gemString) {
                        case "10":
                            sockets.add(GuardianMod.socketTypes.RED);
                            break;
                        case "11":
                            sockets.add(GuardianMod.socketTypes.GREEN);
                            break;
                        case "12":
                            sockets.add(GuardianMod.socketTypes.ORANGE);
                            break;
                        case "13":
                            sockets.add(GuardianMod.socketTypes.WHITE);
                            break;
                        case "14":
                            sockets.add(GuardianMod.socketTypes.CYAN);
                            break;
                        case "15":
                            sockets.add(GuardianMod.socketTypes.BLUE);
                            break;
                        case "16":
                            sockets.add(GuardianMod.socketTypes.CRIMSON);
                            break;
                        case "17":
                            sockets.add(GuardianMod.socketTypes.FRAGMENTED);
                            break;
                        case "18":
                            sockets.add(GuardianMod.socketTypes.PURPLE);
                            break;
                        case "19":
                            sockets.add(GuardianMod.socketTypes.SYNTHETIC);
                            break;
                        case "20":
                            sockets.add(GuardianMod.socketTypes.YELLOW);
                            break;
                        case "21":
                            sockets.add(GuardianMod.socketTypes.LIGHTBLUE);
                            break;
                        default:
                            sockets.add(GuardianMod.socketTypes.RED);
                            break;
                    }

                    miscString = miscString.substring(2);
                    GuardianMod.logger.info("New misc gem load: " + this.name + " new misc = " + miscString);

                }

                this.updateDescription();

            }


        }
    }

    public void addGemToSocket(AbstractGuardianCard gem) {
        AbstractDungeon.player.masterDeck.removeCard(gem);
        sockets.add(gem.thisGemsType);
        this.updateDescription();
        this.saveGemMisc();
    }

    public void updateDescription() {
        //Overwritten in cards
    }

    public void useGems(AbstractPlayer p, AbstractMonster m) {
        for (GuardianMod.socketTypes gem :
                sockets) {
            switch (gem) {
                case RED:
                    Gem_Red.gemEffect(p, m);
                    break;
                case GREEN:
                    Gem_Green.gemEffect(p, m);
                    break;
                case LIGHTBLUE:
                    Gem_Lightblue.gemEffect(p, m);
                    break;
                case ORANGE:
                    Gem_Orange.gemEffect(p, m);
                    break;
                case WHITE:
                    Gem_White.gemEffect(p, m);
                    break;
                case CYAN:
                    Gem_Cyan.gemEffect(p, m);
                    break;
                case BLUE:
                    Gem_Blue.gemEffect(p, m);
                    break;
                case CRIMSON:
                    Gem_Crimson.gemEffect(p, m);
                    break;
                case FRAGMENTED:
                    Gem_Fragmented.gemEffect(p, m);
                    break;
                case PURPLE:
                    Gem_Purple.gemEffect(p, m);
                    break;
                case SYNTHETIC:
                    Gem_Synthetic.gemEffect(p, m);
                    break;
                case YELLOW:
                    Gem_Yellow.gemEffect(p, m);
                    break;
            }
        }
    }

    public String updateGemDescription(String desc, Boolean after) {
        String addedDesc = "";

        for (int i = 0; i < this.socketCount; i++) {
            if (this.sockets.size() > i) {
                GuardianMod.socketTypes gem = this.sockets.get(i);
                if (after) addedDesc = addedDesc + " NL ";
                switch (gem) {
                    case RED:
                        addedDesc = addedDesc + Gem_Red.UPGRADED_DESCRIPTION;
                        break;
                    case GREEN:
                        addedDesc = addedDesc + Gem_Green.UPGRADED_DESCRIPTION;
                        break;
                    case LIGHTBLUE:
                        addedDesc = addedDesc + Gem_Lightblue.UPGRADED_DESCRIPTION;
                        break;
                    case ORANGE:
                        addedDesc = addedDesc + Gem_Orange.UPGRADED_DESCRIPTION;
                        break;
                    case WHITE:
                        addedDesc = addedDesc + Gem_White.UPGRADED_DESCRIPTION;
                        break;
                    case CYAN:
                        addedDesc = addedDesc + Gem_Cyan.UPGRADED_DESCRIPTION;
                        break;
                    case BLUE:
                        addedDesc = addedDesc + Gem_Blue.UPGRADED_DESCRIPTION;
                        break;
                    case CRIMSON:
                        addedDesc = addedDesc + Gem_Crimson.UPGRADED_DESCRIPTION;
                        break;
                    case FRAGMENTED:
                        addedDesc = addedDesc + Gem_Fragmented.UPGRADED_DESCRIPTION;
                        break;
                    case PURPLE:
                        addedDesc = addedDesc + Gem_Purple.UPGRADED_DESCRIPTION;
                        break;
                    case SYNTHETIC:
                        addedDesc = addedDesc + Gem_Synthetic.UPGRADED_DESCRIPTION;
                        break;
                    case YELLOW:
                        addedDesc = addedDesc + Gem_Yellow.UPGRADED_DESCRIPTION;
                        break;
                }
                if (!after) addedDesc = addedDesc + " NL ";
            } else {
                if (after) addedDesc = addedDesc + " NL ";
                addedDesc = addedDesc + CardCrawlGame.languagePack.getCharacterString("Guardian").TEXT[2];
                if (!after) addedDesc = addedDesc + " NL ";
            }
        }

        if (after) {
            return desc + addedDesc;
        } else {
            return addedDesc + desc;

        }

    }

    @Override
    public void render(SpriteBatch sb) {
        super.render(sb);
        if (socketCount > 0 && !isFlipped) {
            Texture socketTexture = null;
            for (int i = 0; i < socketCount; i++) {

                if (sockets.size() > i) {
                    //  GuardianMod.logger.info("Calculating needs for socket: " + sockets.get(i) + " Index = " + i + " on " + this.name + " sockets array size: " + sockets.size() + " textures array size: " + GuardianMod.socketTextures.size());

                    switch (sockets.get(i)) {
                        case RED:
                            //   GuardianMod.logger.info("case RED");
                            if (i == 0) socketTexture = GuardianMod.socketTextures.get(1);
                            else if (i == 1) socketTexture = GuardianMod.socketTextures2.get(1);
                            else if (i == 2) socketTexture = GuardianMod.socketTextures3.get(1);
                            else socketTexture = GuardianMod.socketTextures4.get(1);
                            //   GuardianMod.logger.info("texture is " + socketTexture);
                            break;
                        case GREEN:
                            // GuardianMod.logger.info("case BLUE");
                            if (i == 0) socketTexture = GuardianMod.socketTextures.get(2);
                            else if (i == 1) socketTexture = GuardianMod.socketTextures2.get(2);
                            else if (i == 2) socketTexture = GuardianMod.socketTextures3.get(2);
                            else socketTexture = GuardianMod.socketTextures4.get(2);
                            // GuardianMod.logger.info("texture is " + socketTexture);
                            break;
                        case ORANGE:
                            //  GuardianMod.logger.info("case GREEN");
                            if (i == 0) socketTexture = GuardianMod.socketTextures.get(3);
                            else if (i == 1) socketTexture = GuardianMod.socketTextures2.get(3);
                            else if (i == 2) socketTexture = GuardianMod.socketTextures3.get(3);
                            else socketTexture = GuardianMod.socketTextures4.get(3);
                            //    GuardianMod.logger.info("texture is " + socketTexture);
                            break;
                        case WHITE:
                            //  GuardianMod.logger.info("case GREEN");
                            if (i == 0) socketTexture = GuardianMod.socketTextures.get(4);
                            else if (i == 1) socketTexture = GuardianMod.socketTextures2.get(4);
                            else if (i == 2) socketTexture = GuardianMod.socketTextures3.get(4);
                            else socketTexture = GuardianMod.socketTextures4.get(4);
                            //    GuardianMod.logger.info("texture is " + socketTexture);
                            break;
                        case CYAN:
                            //  GuardianMod.logger.info("case GREEN");
                            if (i == 0) socketTexture = GuardianMod.socketTextures.get(5);
                            else if (i == 1) socketTexture = GuardianMod.socketTextures2.get(5);
                            else if (i == 2) socketTexture = GuardianMod.socketTextures3.get(5);
                            else socketTexture = GuardianMod.socketTextures4.get(5);
                            //    GuardianMod.logger.info("texture is " + socketTexture);
                            break;
                        case BLUE:
                            //  GuardianMod.logger.info("case GREEN");
                            if (i == 0) socketTexture = GuardianMod.socketTextures.get(6);
                            else if (i == 1) socketTexture = GuardianMod.socketTextures2.get(6);
                            else if (i == 2) socketTexture = GuardianMod.socketTextures3.get(6);
                            else socketTexture = GuardianMod.socketTextures4.get(6);
                            //    GuardianMod.logger.info("texture is " + socketTexture);
                            break;
                        case CRIMSON:
                            //  GuardianMod.logger.info("case GREEN");
                            if (i == 0) socketTexture = GuardianMod.socketTextures.get(7);
                            else if (i == 1) socketTexture = GuardianMod.socketTextures2.get(7);
                            else if (i == 2) socketTexture = GuardianMod.socketTextures3.get(7);
                            else socketTexture = GuardianMod.socketTextures4.get(7);
                            //    GuardianMod.logger.info("texture is " + socketTexture);
                            break;
                        case FRAGMENTED:
                            //  GuardianMod.logger.info("case GREEN");
                            if (i == 0) socketTexture = GuardianMod.socketTextures.get(8);
                            else if (i == 1) socketTexture = GuardianMod.socketTextures2.get(8);
                            else if (i == 2) socketTexture = GuardianMod.socketTextures3.get(8);
                            else socketTexture = GuardianMod.socketTextures4.get(8);
                            //    GuardianMod.logger.info("texture is " + socketTexture);
                            break;
                        case PURPLE:
                            //  GuardianMod.logger.info("case GREEN");
                            if (i == 0) socketTexture = GuardianMod.socketTextures.get(9);
                            else if (i == 1) socketTexture = GuardianMod.socketTextures2.get(9);
                            else if (i == 2) socketTexture = GuardianMod.socketTextures3.get(9);
                            else socketTexture = GuardianMod.socketTextures4.get(9);
                            //    GuardianMod.logger.info("texture is " + socketTexture);
                            break;
                        case SYNTHETIC:
                            //  GuardianMod.logger.info("case GREEN");
                            if (i == 0) socketTexture = GuardianMod.socketTextures.get(10);
                            else if (i == 1) socketTexture = GuardianMod.socketTextures2.get(10);
                            else if (i == 2) socketTexture = GuardianMod.socketTextures3.get(10);
                            else socketTexture = GuardianMod.socketTextures4.get(10);
                            //    GuardianMod.logger.info("texture is " + socketTexture);
                            break;
                        case YELLOW:
                            //  GuardianMod.logger.info("case GREEN");
                            if (i == 0) socketTexture = GuardianMod.socketTextures.get(11);
                            else if (i == 1) socketTexture = GuardianMod.socketTextures2.get(11);
                            else if (i == 2) socketTexture = GuardianMod.socketTextures3.get(11);
                            else socketTexture = GuardianMod.socketTextures4.get(11);
                            //    GuardianMod.logger.info("texture is " + socketTexture);
                            break;
                        case LIGHTBLUE:
                            // GuardianMod.logger.info("case BLUE");
                            if (i == 0) socketTexture = GuardianMod.socketTextures.get(12);
                            else if (i == 1) socketTexture = GuardianMod.socketTextures2.get(12);
                            else if (i == 2) socketTexture = GuardianMod.socketTextures3.get(12);
                            else socketTexture = GuardianMod.socketTextures4.get(12);
                            // GuardianMod.logger.info("texture is " + socketTexture);
                            break;
                    }

                } else {

                    if (i == 0) socketTexture = GuardianMod.socketTextures.get(0);
                    else if (i == 1) socketTexture = GuardianMod.socketTextures2.get(0);
                    else if (i == 2) socketTexture = GuardianMod.socketTextures3.get(0);
                    else socketTexture = GuardianMod.socketTextures4.get(0);
                }

                // GuardianMod.logger.info("reached socket texture call, texture is " + socketTexture);
                if (socketTexture != null) renderSocket(sb, socketTexture, i);
                //  GuardianMod.logger.info("passed socket texture call");
            }
        }

    }

    private void renderSocket(SpriteBatch sb, Texture baseTexture, Integer i) {
        //GuardianMod.logger.info("Attempting to render socket: " + baseTexture + " on " + this.name);
        float scale = this.drawScale * Settings.scale;
        float drawX = this.current_x - 256.0F;
        float drawY = this.current_y - 256.0F;
        // GuardianMod.logger.info(this.angle);
        sb.draw(baseTexture, drawX, drawY, 256.0F, 256.0F, 512.0F, 512.0F, this.drawScale * Settings.scale, this.drawScale * Settings.scale, this.angle, 0, 0, 512, 512, false, false);

    }


    public void upgradeMultihit() {
        if (GuardianMod.getMultihitModifiers() > 0) {
            this.upgradeMulthit = GuardianMod.getMultihitModifiers();
            if (this.upgradeMulthit > 0) this.isMultihitModified = true;
        }

    }

    public float calculateBeamDamage() {
        int bonus = 0;

        if (AbstractDungeon.player != null) {
            if (AbstractDungeon.player.hasPower(BeamBuffPower.POWER_ID)) {
                bonus = AbstractDungeon.player.getPower(BeamBuffPower.POWER_ID).amount;
            }
        }
        return bonus;
    }


}